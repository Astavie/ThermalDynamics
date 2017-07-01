package cofh.thermaldynamics.duct.light;

import cofh.core.network.PacketCoFHBase;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermaldynamics.duct.Attachment;
import cofh.thermaldynamics.duct.Duct;
import cofh.thermaldynamics.duct.tiles.DuctToken;
import cofh.thermaldynamics.duct.tiles.DuctUnit;
import cofh.thermaldynamics.duct.tiles.TileGrid;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DuctUnitLight extends DuctUnit<DuctUnitLight, GridLight, Void> {

	private static final Void[] voids = new Void[6];

	@Override
	protected Void[] createTileCache() {

		return voids;
	}

	@Override
	protected DuctUnitLight[] createDuctCache() {

		return new DuctUnitLight[6];
	}

	public DuctUnitLight(TileGrid parent, Duct duct) {

		super(parent, duct);
	}

	@Override
	public boolean isInputTile(@Nullable TileEntity tile, byte side) {

		BlockPos offset = pos().offset(EnumFacing.values()[side]);
		return world().isBlockLoaded(offset) && world().getBlockState(offset).canProvidePower();
	}

	@Nonnull
	@Override
	public DuctToken<DuctUnitLight, GridLight, Void> getToken() {

		return DuctToken.LIGHT;
	}

	@Override
	public GridLight createGrid() {

		return new GridLight(world());
	}

	@Override
	public boolean canConnectToOtherDuct(DuctUnit<DuctUnitLight, GridLight, Void> adjDuct, byte side, byte oppositeSide) {

		return true;
	}

	@Nullable
	@Override
	public Void cacheTile(@Nonnull TileEntity tile, byte side) {

		return null;
	}

	boolean lit = false;

	@Override
	public int getLightValue() {

		if (isLit()) {
			return 15 - (lightingUpdate != null && lightingUpdate != this ? 1 : 0);
		}
		return 0;
	}

	// the logic for this field is required to ensure lighting is propagated the full distance for all nearby ducts
	// the lighting code is incapable of handling when a bunch of adjacent blocks all update state simultaneously
	private static DuctUnitLight lightingUpdate = null;

	protected void updateLighting() {

		lightingUpdate = this;
		parent.updateLighting();
		lightingUpdate = null;
	}

	public boolean isLit() {

		return ServerHelper.isClientWorld(world()) || grid == null ? lit : grid.lit;
	}

	@Override
	public void onPlaced(EntityLivingBase living, ItemStack stack) {

		super.onPlaced(living, stack);
		if (ServerHelper.isServerWorld(world())) {
			lit = world().isBlockPowered(pos());
		}
	}

	@Override
	public void onNeighborBlockChange() {

		super.onNeighborBlockChange();

		if (ServerHelper.isClientWorld(world())) {
			return;
		}
		lit = false;
		EnumFacing[] valid_directions = EnumFacing.VALUES;
		for (int i = 0; !lit && i < valid_directions.length; i++) {
			Attachment attachment = parent.getAttachment(i);
			if (attachment != null && attachment.shouldRSConnect()) {
				continue;
			}

			EnumFacing dir = valid_directions[i];
			lit = world().isSidePowered(pos().offset(dir), dir);
		}
		if (grid != null && grid.lit != lit) {
			grid.upToDate = false;
		}
	}

	@Override
	public void writeToTilePacket(PacketCoFHBase packet) {

		packet.addBool(lit || (grid != null && grid.lit));
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void handleTilePacket(PacketCoFHBase payload) {

		super.handleTilePacket(payload);
		boolean b = payload.getBool();

		if (b != lit) {
			lit = b;
			checkLight();
		}
	}

	@Override
	public TextureAtlasSprite getBaseIcon() {

		return super.getBaseIcon();
	}

	public void checkLight() {

		updateLighting();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		nbt.setBoolean("isLit", lit);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		lit = nbt.getBoolean("isLit");
	}
}
