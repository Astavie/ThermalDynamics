package cofh.thermaldynamics.gui.container;

import cofh.thermaldynamics.duct.Attachment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;

public class ContainerAttachmentBase extends ContainerTDBase {

	Attachment baseTile;

	public ContainerAttachmentBase() {

	}

	public ContainerAttachmentBase(Attachment tile) {

		baseTile = tile;
	}

	public ContainerAttachmentBase(InventoryPlayer inventory, Attachment tile) {

		baseTile = tile;

		/* Player Inventory */
		addPlayerInventory(inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return baseTile == null || baseTile.isUseable(player);
	}

	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();

		if (baseTile == null) {
			return;
		}
		baseTile.sendGuiNetworkData(this, listeners, false);
	}

	@Override
	public void addListener(IContainerListener listener) {

		super.addListener(listener);
		baseTile.sendGuiNetworkData(this, listeners, true);
	}

	@Override
	public void updateProgressBar(int i, int j) {

		if (baseTile == null) {
			return;
		}
		baseTile.receiveGuiNetworkData(i, j);
	}

	@Override
	public int numTileSlots() {

		return baseTile == null ? 0 : baseTile.getInvSlotCount();
	}

}
