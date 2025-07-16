package net.lemoon.tactitech.item;

import net.minecraft.item.ItemStack;

import net.lemoon.tactitech.pipe.PartSpPipe;
import net.lemoon.tactitech.pipe.PipeSpDef;

public class ItemPipePart extends ItemSimplePart {
    public ItemPipePart(Settings settings, PipeSpDef definition) {
        super(settings, definition, (c, h) -> new PartSpPipe(definition, h));
        definition.setPickStack(new ItemStack(this));
    }
}
