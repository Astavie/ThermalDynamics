package cofh.thermaldynamics.gui.slot;

import cofh.api.item.ISpecialFilterFluid;
import cofh.core.util.helpers.FluidHelper;
import cofh.thermaldynamics.duct.attachments.filter.IFilterConfig;
import net.minecraft.item.ItemStack;

public class SlotFilterFluid extends SlotFilter {

	public SlotFilterFluid(IFilterConfig tile, int slotIndex, int x, int y) {

		super(tile, slotIndex, x, y);
	}

	@Override
	public void putStack(ItemStack stack) {

		if (stack.isEmpty() || isItemValid(stack)) {
			super.putStack(stack);
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack) {

		return !stack.isEmpty() && (FluidHelper.getFluidForFilledItem(stack) != null || stack.getItem() instanceof ISpecialFilterFluid);
	}

}
