package org.lushplugins.regrowthnpcs.gui;

import me.tofaa.entitylib.meta.EntityMeta;
import org.lushplugins.configurablenpcs.ConfigurableNPCs;
import org.lushplugins.configurablenpcs.entity.component.EntityComponent;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializer;
import org.lushplugins.guihandler.annotation.*;
import org.lushplugins.guihandler.gui.GuiAction;
import org.lushplugins.guihandler.slot.Slot;
import org.lushplugins.regrowthnpcs.npc.NPC;

import java.util.List;

@CustomGui
public class EditorGui {

    @GuiActionHandler(GuiAction.REFRESH)
    public void updateEditIcons(@Provided NPC npc, @LabelledSlots('e') List<Slot> slots) {
        List<EntityComponent> components = npc.getEntityConfig().components();

        EntityMeta meta = npc.getEntity().getEntityMeta();
        List<EntityMetaSerializer<?>> serializers = ConfigurableNPCs.metaSerializers().getSerializersApplicableTo(meta);
        for (int i = 0; i < slots.size(); i++) {
            if (serializers.size() < i) {
                return;
            }

            EntityMetaSerializer<?> serializer = serializers.get(i);
        }
    }
}
