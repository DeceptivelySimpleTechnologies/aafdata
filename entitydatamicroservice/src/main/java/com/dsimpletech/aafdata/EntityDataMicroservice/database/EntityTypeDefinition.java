package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import java.sql.Timestamp;


public class EntityTypeDefinition {

    int Id;
//    UUID Uuid;
    int EntitySubtypeId;
    String TextKey;

    String LocalizedName;
//    String LocalizedDescription;
//    String LocalizedAbbreviation;

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

    public EntityTypeDefinition(int id, int entitySubtypeId, String textKey, String localizedName, int ordinal, boolean isActive, Timestamp deletedAtDateTimeUtc) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        LocalizedName = localizedName;
        Ordinal = ordinal;
        IsActive = isActive;
        DeletedAtDateTimeUtc = deletedAtDateTimeUtc;
    }
}
