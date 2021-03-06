/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.mixin.core.item;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.translation.Translation;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.mod.util.TranslationHelper;

@NonnullByDefault
@Mixin(Item.class)
@Implements(@Interface(iface = ItemType.class, prefix = "item$"))
public abstract class MixinItemType implements ItemType {

    @Shadow
    public abstract int getItemStackLimit();

    @Shadow(prefix = "shadow$")
    public abstract String shadow$getUnlocalizedName();

    @Shadow
    private int maxDamage;

    @Shadow
    private boolean hasSubtypes;

    @Override
    public String getId() {
        return Item.itemRegistry.getNameForObject(this).toString();
    }

    @Override
    public Translation getTranslation() {
        String id = shadow$getUnlocalizedName();
        String name = ("" + StatCollector.translateToLocal(id + ".name")).trim();

        return TranslationHelper.createStaticTranslation(id, name);
    }

    @Override
    public int getMaxStackQuantity() {
        return getItemStackLimit();
    }

    @Override
    public int getMaxDamage() {
        return this.maxDamage;
    }

    public boolean item$isDamageable() {
        return this.maxDamage > 0 && !this.hasSubtypes;
    }
}
