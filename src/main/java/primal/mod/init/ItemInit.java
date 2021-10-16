package primal.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import primal.mod.items.PrimalFear;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item PRIMAL_I = new PrimalFear("primal", 8);
	public static final Item PRIMAL_II = new PrimalFear("primal2", 12);
	public static final Item PRIMAL_III = new PrimalFear("primal3", 16);
	public static final Item PRIMAL_IV = new PrimalFear("primal4", 20);
	public static final Item PRIMAL_V = new PrimalFear("primal5", 24);
}
	