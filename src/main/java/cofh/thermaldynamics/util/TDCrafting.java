package cofh.thermaldynamics.util;

import cofh.api.util.ThermalExpansionHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermaldynamics.ThermalDynamics;
import cofh.thermaldynamics.duct.Duct;
import cofh.thermaldynamics.duct.DuctItem;
import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermaldynamics.init.TDItems;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;
import static cofh.lib.util.helpers.ItemHelper.ShapelessRecipe;

public class TDCrafting {

	public static boolean enableCoverRecipes = true;
	public static boolean useHardenedGlass = true;
	public static boolean useTransposerRecipes = true;

	public static void loadRecipes() {

		enableCoverRecipes = ThermalDynamics.CONFIG.get("Attachment.Cover", "Recipe", true);
		useHardenedGlass = ThermalDynamics.CONFIG.get("Duct.Recipes", "UseHardenedGlass", true);
		useTransposerRecipes = ThermalDynamics.CONFIG.get("Duct.Recipes", "UseFluidTransposer", true);
		String glassHardened = useHardenedGlass ? "blockGlassHardened" : "blockGlass";

		/* ENERGY */
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.energyBasic.itemStack, 6), "RRR", "IGI", "RRR", 'I', "ingotLead", 'G', "blockGlass", 'R', "dustRedstone"));
		GameRegistry.addRecipe(ShapelessRecipe(TDDucts.energyHardened.itemStack, TDDucts.energyBasic.itemStack, "dustRedstone", "nuggetInvar", "nuggetInvar", "nuggetInvar"));
		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.energyHardened.itemStack, 3), TDDucts.energyBasic.itemStack, TDDucts.energyBasic.itemStack, TDDucts.energyBasic.itemStack, "dustRedstone", "dustRedstone", "dustRedstone", "ingotInvar"));

		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.energyReinforcedEmpty.itemStack, 6), "IGI", 'I', "ingotElectrum", 'G', glassHardened));

		GameRegistry.addRecipe(ShapelessRecipe(TDDucts.energySignalum.itemStack, TDDucts.energyReinforced.itemStack, "dustRedstone", "nuggetSignalum", "nuggetSignalum", "nuggetSignalum"));

		GameRegistry.addRecipe(ShapelessRecipe(TDDucts.energyResonant.itemStack, TDDucts.energySignalum.itemStack, "dustRedstone", "nuggetEnderium", "nuggetEnderium", "nuggetEnderium"));

		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.energySignalumEmpty.itemStack, 6), "IGI", 'I', "ingotSignalum", 'G', glassHardened));

		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.energySignalum.itemStack, 3), TDDucts.energyReinforced.itemStack, TDDucts.energyReinforced.itemStack, TDDucts.energyReinforced.itemStack, "dustRedstone", "dustRedstone", "dustRedstone", "ingotSignalum"));
		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.energyResonant.itemStack, 3), TDDucts.energySignalum.itemStack, TDDucts.energySignalum.itemStack, TDDucts.energySignalum.itemStack, "dustRedstone", "dustRedstone", "dustRedstone", "ingotEnderium"));
		//		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.energyResonant.itemStack, 3), TDDucts.energyReinforced.itemStack, TDDucts.energyReinforced.itemStack, TDDucts.energyReinforced.itemStack, "dustRedstone", "dustRedstone", "dustRedstone", "ingotEnderium"));

		GameRegistry.addRecipe(ShapelessRecipe(TDDucts.energyResonantEmpty.itemStack, TDDucts.energyReinforcedEmpty.itemStack, "dustRedstone", "nuggetEnderium", "nuggetEnderium", "nuggetEnderium"));
		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.energyResonantEmpty.itemStack, 3), TDDucts.energyReinforcedEmpty.itemStack, TDDucts.energyReinforcedEmpty.itemStack, TDDucts.energyReinforcedEmpty.itemStack, "dustRedstone", "dustRedstone", "dustRedstone", "ingotEnderium"));

		GameRegistry.addRecipe(ShapedRecipe(TDDucts.energySuperCondEmpty.itemStack, "IGI", "GEG", "IGI", 'I', "ingotElectrum", 'G', glassHardened, 'E', TDDucts.energyReinforced.itemStack));

		/* ENERGY - TE Integration */
		ThermalExpansionHelper.addPulverizerRecipe(1600, TDDucts.energyBasic.itemStack, new ItemStack(Items.REDSTONE), ItemHelper.cloneStack(ItemMaterial.nuggetLead, 3));
		ThermalExpansionHelper.addPulverizerRecipe(1600, TDDucts.energyHardened.itemStack, new ItemStack(Items.REDSTONE, 2), ItemHelper.cloneStack(ItemMaterial.nuggetInvar, 3));

		addTransposerFill(800, TDDucts.energyReinforcedEmpty.itemStack, TDDucts.energyReinforced.itemStack, new FluidStack(TFFluids.fluidRedstone, 200), false);
		addTransposerFill(800, TDDucts.energySignalumEmpty.itemStack, TDDucts.energySignalum.itemStack, new FluidStack(TFFluids.fluidRedstone, 200), false);
		addTransposerFill(800, TDDucts.energyResonantEmpty.itemStack, TDDucts.energyResonant.itemStack, new FluidStack(TFFluids.fluidRedstone, 200), false);
		addTransposerFill(4000, TDDucts.energySuperCondEmpty.itemStack, TDDucts.energySuperCond.itemStack, new FluidStack(TFFluids.fluidCryotheum, 500), false);

		/* FLUID */
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.fluidBasic.itemStack, 6), "IGI", 'I', "ingotCopper", 'G', "blockGlass"));
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.fluidBasicOpaque.itemStack, 6), "IGI", 'I', "ingotCopper", 'G', "ingotLead"));

		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.fluidHardened.itemStack, 6), "IGI", 'I', "ingotInvar", 'G', glassHardened));
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.fluidHardenedOpaque.itemStack, 6), "IGI", 'I', "ingotInvar", 'G', "ingotLead"));

		GameRegistry.addRecipe(ShapelessRecipe(TDDucts.fluidEnergy.itemStack, TDDucts.fluidHardened.itemStack, "nuggetSignalum", "nuggetSignalum", "nuggetSignalum", "nuggetElectrum", "nuggetElectrum", "nuggetElectrum"));
		GameRegistry.addRecipe(ShapelessRecipe(TDDucts.fluidEnergyOpaque.itemStack, TDDucts.fluidHardenedOpaque.itemStack, "nuggetSignalum", "nuggetSignalum", "nuggetSignalum", "nuggetElectrum", "nuggetElectrum", "nuggetElectrum"));

		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.fluidEnergy.itemStack, 3), TDDucts.fluidHardened.itemStack, TDDucts.fluidHardened.itemStack, TDDucts.fluidHardened.itemStack, "ingotSignalum", "ingotElectrum"));
		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.fluidEnergyOpaque.itemStack, 3), TDDucts.fluidHardenedOpaque.itemStack, TDDucts.fluidHardenedOpaque.itemStack, TDDucts.fluidHardenedOpaque.itemStack, "ingotSignalum", "ingotElectrum"));

		GameRegistry.addRecipe(ShapedRecipe(TDDucts.fluidSuper.itemStack, "IGI", "GEG", "IGI", 'I', "ingotBronze", 'G', glassHardened, 'E', TDDucts.fluidHardened.itemStack));
		GameRegistry.addRecipe(ShapedRecipe(TDDucts.fluidSuperOpaque.itemStack, "IGI", "GEG", "IGI", 'I', "ingotBronze", 'G', glassHardened, 'E', TDDucts.fluidHardenedOpaque.itemStack));

		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.fluidSuper.itemStack, 6), "PlP", "IGI", "PlP", 'I', "ingotLumium", 'G', glassHardened, 'P', "ingotPlatinum", 'l', "nuggetLead"));
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.fluidSuperOpaque.itemStack, 6), "PlP", "IGI", "PlP", 'I', "ingotLumium", 'G', "ingotLead", 'P', "ingotPlatinum", 'l', "nuggetLead"));

		/* FLUID - TE Integration */
		ThermalExpansionHelper.addPulverizerRecipe(1600, TDDucts.fluidBasic.itemStack, ItemHelper.cloneStack(ItemMaterial.nuggetCopper, 3));
		ThermalExpansionHelper.addPulverizerRecipe(1600, TDDucts.fluidBasicOpaque.itemStack, ItemHelper.cloneStack(ItemMaterial.nuggetCopper, 3), ItemHelper.cloneStack(ItemMaterial.nuggetLead));

		/* ITEMS */
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.itemBasic.itemStack, 6), "IGI", 'I', "ingotTin", 'G', glassHardened));
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.itemBasicOpaque.itemStack, 6), "IGI", 'I', "ingotTin", 'G', "ingotLead"));

		//		TODO: Readd Omni/Warp Ducts
		//		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.itemOmni.itemStack, 2), TDDucts.itemBasic.itemStack, TDDucts.itemBasic.itemStack, "nuggetEnderium", "nuggetEnderium", "nuggetEnderium"));
		//		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.itemOmniOpaque.itemStack, 2), TDDucts.itemBasicOpaque.itemStack, TDDucts.itemBasicOpaque.itemStack, "nuggetEnderium", "nuggetEnderium", "nuggetEnderium"));
		//		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.itemOmni.itemStack, 6), TDDucts.itemBasic.itemStack, TDDucts.itemBasic.itemStack, TDDucts.itemBasic.itemStack, TDDucts.itemBasic.itemStack, TDDucts.itemBasic.itemStack, TDDucts.itemBasic.itemStack, "ingotEnderium"));
		//		GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(TDDucts.itemOmniOpaque.itemStack, 6), TDDucts.itemBasicOpaque.itemStack, TDDucts.itemBasicOpaque.itemStack, TDDucts.itemBasicOpaque.itemStack, TDDucts.itemBasicOpaque.itemStack, TDDucts.itemBasicOpaque.itemStack, TDDucts.itemBasicOpaque.itemStack, "ingotEnderium"));

		/* ITEMS - TE Integration */
		addTransposerFill(800, TDDucts.itemBasic.itemStack, TDDucts.itemFast.itemStack, new FluidStack(TFFluids.fluidGlowstone, 200), false);
		addTransposerFill(800, TDDucts.itemBasicOpaque.itemStack, TDDucts.itemFastOpaque.itemStack, new FluidStack(TFFluids.fluidGlowstone, 200), false);
		addTransposerFill(800, TDDucts.itemBasic.itemStack, TDDucts.itemEnergy.itemStack, new FluidStack(TFFluids.fluidRedstone, 200), false);
		addTransposerFill(800, TDDucts.itemBasicOpaque.itemStack, TDDucts.itemEnergyOpaque.itemStack, new FluidStack(TFFluids.fluidRedstone, 200), false);

		/* STRUCTURE */
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.structure.itemStack, 6), "iIi", 'i', "nuggetIron", 'I', "ingotLead"));

		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.lightDuct.itemStack, 6), "LIL", 'L', "ingotLumium", 'I', "ingotLead"));

		/* TRANSPORT */

		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.transportFrame.itemStack, 4), "IGI", "G G", "IGI", 'I', "ingotElectrum", 'G', glassHardened));
		addTransposerFill(800, TDDucts.transportFrame.itemStack, TDDucts.transportBasic.itemStack, new FluidStack(TFFluids.fluidAerotheum, 50), false);
		addTransposerFill(32000, TDDucts.transportBasic.itemStack, TDDucts.transportCrossover.itemStack, new FluidStack(TFFluids.fluidEnder, 1000), false);
		GameRegistry.addRecipe(ShapedRecipe(ItemHelper.cloneStack(TDDucts.transportLongRange.itemStack, 8), "IGI", "G G", "IGI", 'I', "ingotCopper", 'G', glassHardened));

		/* COVERS */
		if (enableCoverRecipes) {
			GameRegistry.addRecipe(RecipeCover.instance);
		}

		/* SIGNALLER */

		GameRegistry.addRecipe(ShapedRecipe(new ItemStack(TDItems.itemRelay, 2), "iGi", "IRI", 'R', "dustRedstone", 'G', "gemQuartz", 'I', "ingotLead", 'i', "nuggetSignalum"));

		/* ATTACHMENTS */
		String[] materials = { "Iron", "Invar", "Electrum", "Signalum", "Enderium" };

		int hardGlassLevel = useHardenedGlass ? 2 : 5; // level to start using hardened glass

		for (int i = 0; i < materials.length; i++) {
			GameRegistry.addRecipe(ShapedRecipe(new ItemStack(TDItems.itemServo, 2, i), "iGi", "IRI", 'R', "dustRedstone", 'G', i < hardGlassLevel ? "blockGlass" : "blockGlassHardened", 'I', "ingot" + materials[i], 'i', "nuggetIron"));

			GameRegistry.addRecipe(ShapedRecipe(new ItemStack(TDItems.itemFilter, 2, i), "iGi", "IRI", 'R', Items.PAPER, 'G', i < hardGlassLevel ? "blockGlass" : "blockGlassHardened", 'I', "ingot" + materials[i], 'i', "nuggetIron"));

			GameRegistry.addRecipe(ShapedRecipe(new ItemStack(TDItems.itemRetriever, 2, i), "iGi", "IRI", 'R', Items.ENDER_EYE, 'G', i < hardGlassLevel ? "blockGlass" : "blockGlassHardened", 'I', "ingot" + materials[i], 'i', "nuggetGold"));

			if (i > 0) {
				for (Item item : new Item[] { TDItems.itemFilter, TDItems.itemServo, TDItems.itemRetriever }) {
					if (i < hardGlassLevel) {
						GameRegistry.addRecipe(addInputMetaRange(new ShapelessOreRecipe(new ItemStack(item, 1, i), "ingot" + materials[i]), new ItemStack(item, 1), 0, i - 1));
					} else {
						if (i > hardGlassLevel) {
							GameRegistry.addRecipe(addInputMetaRange(new ShapelessOreRecipe(new ItemStack(item, 1, i), "ingot" + materials[i]), new ItemStack(item, 1), hardGlassLevel, i - 1));
						}
						GameRegistry.addRecipe(addInputMetaRange(addInputMetaRange(new ShapelessOreRecipe(new ItemStack(item, 2, i), "blockGlassHardened", "ingot" + materials[i], "ingot" + materials[i]), new ItemStack(item, 1), 0, hardGlassLevel - 1), new ItemStack(item, 1), 0, hardGlassLevel - 1));
					}
				}
			}
		}

		/* CONVERSIONS */
		for (Duct[] duct : new Duct[][] { { TDDucts.itemBasic, TDDucts.itemBasicOpaque }, { TDDucts.itemFast, TDDucts.itemFastOpaque }, { TDDucts.itemEnergy, TDDucts.itemEnergyOpaque },
				//				TODO: Readd Omni/Warp Ducts
				//				{ TDDucts.itemOmni, TDDucts.itemOmniOpaque},
				{ TDDucts.fluidHardened, TDDucts.fluidHardenedOpaque } }) {

			final ItemStack t = duct[0].itemStack;
			final ItemStack o = duct[1].itemStack;

			GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(t, 6), o, o, o, o, o, o, glassHardened));
			GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(o, 6), t, t, t, t, t, t, "ingotLead"));
		}
		for (Duct[] duct : new Duct[][] { { TDDucts.fluidBasic, TDDucts.fluidBasicOpaque } }) {

			final ItemStack t = duct[0].itemStack;
			final ItemStack o = duct[1].itemStack;

			GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(t, 6), o, o, o, o, o, o, "blockGlass"));
			GameRegistry.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(o, 6), t, t, t, t, t, t, "ingotLead"));
		}

		/* DENSE / VACUUM - TE Integration */
		for (DuctItem duct : new DuctItem[] { TDDucts.itemBasic, TDDucts.itemBasicOpaque,
				//				TODO: Readd Omni/Warp Ducts
				//				TDDucts.itemOmni, TDDucts.itemOmniOpaque,
				TDDucts.itemFast, TDDucts.itemFastOpaque, TDDucts.itemEnergy, TDDucts.itemEnergyOpaque }) {
			GameRegistry.addRecipe(ShapelessRecipe(duct.getDenseItemStack(), duct.itemStack, "dustLead"));
			GameRegistry.addRecipe(ShapelessRecipe(duct.getVacuumItemStack(), duct.itemStack, "dustSilver"));
			GameRegistry.addRecipe(ShapelessRecipe(duct.itemStack, duct.getDenseItemStack()));// , "dustCharcoal"));
			GameRegistry.addRecipe(ShapelessRecipe(duct.itemStack, duct.getVacuumItemStack()));// , "dustCharcoal"));
		}
	}

	public static void addTransposerFill(int energy, ItemStack input, ItemStack output, FluidStack fluid, boolean reversible) {

		if (useTransposerRecipes && Loader.isModLoaded("thermalexpansion")) {
			ThermalExpansionHelper.addTransposerFill(energy, input, output, fluid, reversible);
		} else {
			int i = MathHelper.clamp(Fluid.BUCKET_VOLUME / fluid.amount, 1, 8);
			ItemStack fluidBucket = getFluidBucket(fluid);
			if (fluidBucket != null) {
				ShapelessOreRecipe recipe = new ShapelessOreRecipe(ItemHelper.cloneStack(output, i), fluidBucket);

				for (int j = 0; j < i; j++) {
					recipe.getInput().add(input.copy());
				}
				GameRegistry.addRecipe(recipe);
			}
		}
	}

	public static ItemStack getFluidBucket(FluidStack fluidStack) {

		return UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidStack.getFluid());
	}

	public static ShapelessOreRecipe addInputMetaRange(ShapelessOreRecipe recipe, ItemStack input, int minMeta, int maxMeta) {

		ArrayList<ItemStack> itemStacks = new ArrayList<>(maxMeta - minMeta + 1);
		for (int i = minMeta; i <= maxMeta; i++) {
			input = input.copy();
			input.setItemDamage(i);
			itemStacks.add(input);
		}
		recipe.getInput().add(itemStacks);
		return recipe;
	}

	public static ShapelessOreRecipe addInput(ShapelessOreRecipe recipe, Collection<ItemStack>... inputs) {

		for (Collection<ItemStack> input : inputs) {
			ArrayList<ItemStack> itemStacks = new ArrayList<>(input.size());
			itemStacks.addAll(input);
			recipe.getInput().add(itemStacks);
		}
		return recipe;
	}

	public static ShapelessOreRecipe addInput(ShapelessOreRecipe recipe, ItemStack[]... inputs) {

		for (ItemStack[] input : inputs) {
			ArrayList<ItemStack> itemStacks = new ArrayList<>(input.length);
			Collections.addAll(itemStacks, input);
			recipe.getInput().add(itemStacks);
		}
		return recipe;
	}

}
