package org.terasology.VolcanoGiants.systems;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.delay.DelayManager;
import org.terasology.logic.delay.DelayedActionTriggeredEvent;
import org.terasology.logic.delay.PeriodicActionTriggeredEvent;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.InventoryManager;
import org.terasology.logic.inventory.events.GiveItemEvent;
import org.terasology.registry.CoreRegistry;

@RegisterSystem
public class VolcanicAshManager extends BaseComponentSystem{

    DelayManager delayManager = CoreRegistry.get(DelayManager.class);
    static final String PERIODIC_DELETION_OF_ITEM = "Delete item periodically";
    private EntityRef inventory;


    @ReceiveEvent (priority = EventPriority.PRIORITY_HIGH)
    public void onGiveItemToEntity(GiveItemEvent event, EntityRef entity) {
        if (event.getTargetEntity().hasComponent(InventoryComponent.class)) {
            int blockPos = entity.toFullDescription().indexOf("\"VolcanoGiants:VolcanicAsh\"");
            if(blockPos != -1) {
                /*This means the block is VolcanicAsh. Hence, we will delete itimitating ash dispersal.*/
                this.inventory = event.getTargetEntity();
                delayManager.addPeriodicAction(entity, PERIODIC_DELETION_OF_ITEM,
                        10 * 1000, 5000);
            }
        }
    }



    @ReceiveEvent
    public void onGradualDeletion(PeriodicActionTriggeredEvent event, EntityRef entity) {
        InventoryManager inventoryManager = CoreRegistry.get(InventoryManager.class);
        if (event.getActionId().equals(PERIODIC_DELETION_OF_ITEM)) {
            if (inventoryManager.removeItem(inventory, entity, entity, true, 1) == null) {
                delayManager.cancelPeriodicAction(entity, PERIODIC_DELETION_OF_ITEM);
            }
        }
    }
}
