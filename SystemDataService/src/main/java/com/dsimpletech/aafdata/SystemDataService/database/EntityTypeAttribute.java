//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.database;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;


public class EntityTypeAttribute {

    @Getter private long Id;
    //    UUID Uuid;
    @Getter private long EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private String LocalizedName;
    @Getter String LocalizedDescription;
    @Getter String LocalizedAbbreviation;
    @Getter private String LocalizedInformation;
    @Getter private String LocalizedPlaceholder;
    @Getter private boolean IsLocalizable;
    @Getter private boolean IsToBeAssociatedWithEachEntityTypeDefinition;
    @Getter private long GeneralizedDataTypeEntitySubtypeId;
    @Getter private long DataSizeOrMaximumLengthInBytesOrCharacters;
    @Getter private long DataPrecision;
    @Getter private long DataScale;
    @Getter private long KeyTypeEntitySubtypeId;
    @Getter private long RelatedEntityTypeId;
    @Getter private long RelatedEntityTypeAttributeId;
    @Getter private long RelatedEntityTypeCardinalityEntitySubtypeId;
    @Getter private String EntitySubtypeGroupKey;
    @Getter private long ValueEntitySubtypeId;
    @Getter private String DefaultValue;
    @Getter private String MinimumValue;
    @Getter private String MaximumValue;
    @Getter private String RegExValidationPattern;
    @Getter private float StepIncrementValue;
    @Getter private String RemoteValidationMethodAsAjaxUri;
    @Getter private long IndexEntitySubtypeId;
    @Getter private long UniquenessEntitySubtypeId;
    @Getter private long SensitivityEntitySubtypeId;
    @Getter @Setter private Timestamp PublishedAtDateTimeUtc;           //NOTE: @Setter added to enable update during Publish() without re-caching, which releases the database connection, until the Publish() is successful
    @Getter @Setter private long PublishedByInformationSystemUserId;    //NOTE: @Setter added to enable update during Publish() without re-caching, which releases the database connection, until the Publish() is successful

    //    String ResourceName;
    @Getter private long Ordinal;
    @Getter private boolean IsActive;
    //    UUID CorrelationUuid;
    //    String Digest;
    @Getter private Timestamp CreatedAtDateTimeUtc;
    @Getter private long CreatedByInformationSystemUserId;
    @Getter private Timestamp UpdatedAtDateTimeUtc;
    @Getter private long UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
    @Getter private long DeletedByInformationSystemUserId;

    public EntityTypeAttribute(long id, long entitySubtypeId, String textKey, String localizedName, String localizedDescription, String localizedAbbreviation, String localizedInformation, String localizedPlaceholder, boolean isLocalizable, boolean isToBeAssociatedWithEachEntityTypeDefinition, long generalizedDataTypeEntitySubtypeId, long dataSizeOrMaximumLengthInBytesOrCharacters, long dataPrecision, long dataScale, long keyTypeEntitySubtypeId, long relatedEntityTypeId, long relatedEntityTypeAttributeId, long relatedEntityTypeCardinalityEntitySubtypeId, String entitySubtypeGroupKey, long valueEntitySubtypeId, String defaultValue, String minimumValue, String maximumValue, String regExValidationPattern, float stepIncrementValue, String remoteValidationMethodAsAjaxUri, long indexEntitySubtypeId, long uniquenessEntitySubtypeId, long sensitivityEntitySubtypeId, Timestamp publishedAtDateTimeUtc, long publishedByInformationSystemUserId, long ordinal, boolean isActive, Timestamp createdAtDateTimeUtc, long createdByInformationSystemUserId, Timestamp updatedAtDateTimeUtc, long updatedByInformationSystemUserId, Timestamp deletedAtDateTimeUtc, long deletedByInformationSystemUserId) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        LocalizedName = localizedName;
        LocalizedDescription = localizedDescription;
        LocalizedAbbreviation = localizedAbbreviation;
        LocalizedInformation = localizedInformation;
        LocalizedPlaceholder = localizedPlaceholder;
        IsLocalizable = isLocalizable;
        IsToBeAssociatedWithEachEntityTypeDefinition = isToBeAssociatedWithEachEntityTypeDefinition;
        GeneralizedDataTypeEntitySubtypeId = generalizedDataTypeEntitySubtypeId;
        DataSizeOrMaximumLengthInBytesOrCharacters = dataSizeOrMaximumLengthInBytesOrCharacters;
        DataPrecision = dataPrecision;
        DataScale = dataScale;
        KeyTypeEntitySubtypeId = keyTypeEntitySubtypeId;
        RelatedEntityTypeId = relatedEntityTypeId;
        RelatedEntityTypeAttributeId = relatedEntityTypeAttributeId;
        RelatedEntityTypeCardinalityEntitySubtypeId = relatedEntityTypeCardinalityEntitySubtypeId;
        EntitySubtypeGroupKey = entitySubtypeGroupKey;
        ValueEntitySubtypeId = valueEntitySubtypeId;
        DefaultValue = defaultValue;
        MinimumValue = minimumValue;
        MaximumValue = maximumValue;
        RegExValidationPattern = regExValidationPattern;
        StepIncrementValue = stepIncrementValue;
        RemoteValidationMethodAsAjaxUri = remoteValidationMethodAsAjaxUri;
        IndexEntitySubtypeId = indexEntitySubtypeId;
        UniquenessEntitySubtypeId = uniquenessEntitySubtypeId;
        SensitivityEntitySubtypeId = sensitivityEntitySubtypeId;
        PublishedAtDateTimeUtc = publishedAtDateTimeUtc;
        PublishedByInformationSystemUserId = publishedByInformationSystemUserId;
        Ordinal = ordinal;
        IsActive = isActive;
        CreatedAtDateTimeUtc = createdAtDateTimeUtc;
        CreatedByInformationSystemUserId = createdByInformationSystemUserId;
        UpdatedAtDateTimeUtc = updatedAtDateTimeUtc;
        UpdatedByInformationSystemUserId = updatedByInformationSystemUserId;
        DeletedAtDateTimeUtc = deletedAtDateTimeUtc;
        DeletedByInformationSystemUserId = deletedByInformationSystemUserId;
    }
}
