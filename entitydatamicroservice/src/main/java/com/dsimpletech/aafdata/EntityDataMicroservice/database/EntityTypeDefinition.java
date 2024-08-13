package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import lombok.Getter;

import java.sql.Timestamp;


public class EntityTypeDefinition {

    @Getter private int Id;
//    UUID Uuid;
    @Getter private int EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private String LocalizedName;
//    String LocalizedDescription;
//    String LocalizedAbbreviation;

//    String ResourceName;
    @Getter private int Ordinal;
    @Getter private boolean IsActive;
//    UUID CorrelationUuid;
//    String Digest;
//    Timestamp CreatedAtDateTimeUtc;
//    int CreatedByInformationSystemUserId;
//    Timestamp UpdatedAtDateTimeUtc;
//    int UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
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
