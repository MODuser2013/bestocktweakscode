package bestock.general;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabTweaks extends CreativeTabs
{
    CreativeTabTweaks(String par2Str)
    {
        super(par2Str);
    }

    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return Block.brick.blockID;
    }
    public String getTranslatedTabLabel()
    {
    return "user2013 Tweaks"; //The name of the tab ingame
    }
}
