package com.dsimpletech.aafdata.EntityDataMicroservice.database;


import java.sql.Timestamp;

public class EntityTypeDefinitionEntityTypeAttributeAssociation {

    int Id;
//    UUID Uuid;
    int EntitySubtypeId;
    String TextKey;

    int EntityTypeDefinitionId;
    int EntityTypeAttributeId;

//    String ResourceName;
    int Ordinal;
    boolean IsActive;
//    UUID CorrelationUuid;
//    String Digest;
//    Timestamp CreatedAtDateTimeUtc;
//    int CreatedByInformationSystemUserId;
//    Timestamp UpdatedAtDateTimeUtc;
//    int UpdatedByInformationSystemUserId;
    Timestamp DeletedAtDateTimeUtc;
//    int DeletedByInformationSystemUserId;

    public EntityTypeDefinitionEntityTypeAttributeAssociation(int id, int entitySubtypeId, String textKey, int entityTypeDefinitionId, int entityTypeAttributeId, int ordinal, boolean isActive, Timestamp deletedAtDateTimeUtc) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        EntityTypeDefinitionId = entityTypeDefinitionId;
        EntityTypeAttributeId = entityTypeAttributeId;
        Ordinal = ordinal;
        IsActive = isActive;
        DeletedAtDateTimeUtc = deletedAtDateTimeUtc;
    }
}
