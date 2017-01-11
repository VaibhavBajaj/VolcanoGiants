package org.terasology.VolcanoGiants.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.events.GiveItemEvent;

@RegisterSystem
public class VolcanicAshManager {

    Logger logger = LoggerFactory.getLogger(VolcanicAshManager.class);

    @ReceiveEvent (priority = EventPriority.PRIORITY_HIGH)
    public void onGiveItemToEntity(GiveItemEvent event, EntityRef entity) {
        
        if (event.getTargetEntity().hasComponent(InventoryComponent.class)) {
            logger.info("Entity Prefab: " + entity.getParentPrefab());
        }
    }
}
