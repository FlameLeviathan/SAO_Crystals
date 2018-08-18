package crystals;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class Shard {

    ArmorStand armorStand = null;

    public Shard(ItemStack block, int stage){

        EulerAngle angle1 = new EulerAngle(0, 0, 45);
        EulerAngle angle2 = new EulerAngle(45, 0, 45);

        armorStand.setHelmet(block);
        if(stage == 1) {
            armorStand.setHeadPose(angle1);
        } else if(stage == 2){
            armorStand.setHeadPose(angle2);
        }else {
            armorStand.setHeadPose(angle1);
        }


        //armorStand Section
        armorStand.setCustomName("Shard");
        armorStand.setCustomNameVisible(false);
        armorStand.setSmall(true);
        armorStand.setVisible(true);
        armorStand.setGravity(false);
    }


}
