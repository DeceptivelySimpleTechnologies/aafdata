//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;


public class EntitySubtype {

    @Getter private int Id;
//    UUID Uuid;
//    int EntitySubtypeId;
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

    public EntitySubtype(int id, String textKey, String localizedName, int ordinal, boolean isActive, Timestamp deletedAtDateTimeUtc) {
    Id = id;
    TextKey = textKey;
    LocalizedName = localizedName;
    Ordinal = ordinal;
    IsActive = isActive;
    DeletedAtDateTimeUtc = deletedAtDateTimeUtc;
    }
}
