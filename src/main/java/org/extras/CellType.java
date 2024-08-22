package org.extras;

/**
 * CellType enum used to distinguish Cell class and to draw correct sprites
 * @author Xander Smith
 * @version 1.0
 */
public enum CellType {
    EmptyCell,
    WallCell,   // sd, d
    StartCell,  // sd, d
    ExitCell,   // sd, d
    ExitCell_Raft,
    ExitCell_Boat,
    RewardCell_Regular, // sd   d
    RewardCell_Bonus,
    PunishmentCell,
    PunishmentCell_Inactive,
    Pickup_Trap,        // sd   d
    Pickup_Trap_Inactive,
    Pickup_Speed,   // sd   d
    Pickup_Speed_Inactive,
    EnemyTrap,      // sd d
}
