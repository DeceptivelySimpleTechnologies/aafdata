//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.database;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;


public class EntityTypeAttribute {

    @Getter private long Id;
    //    UUID Uuid;
    @Getter private long EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private String LocalizedName;
    //    String LocalizedDescription;
    //    String LocalizedAbbreviation;
    @Getter private String LocalizedInformation;
    @Getter private String LocalizedPlaceholder;
    @Getter private boolean IsLocalizable;
    @Getter private long GeneralizedDataTypeEntitySubtypeId;
    //    (10, 'f6818d77-64c2-487e-bcc9-989872509988', 0, 'entitysubtype-entitytypeattribute-generalizeddatatype-boolean', 2, 'GeneralizedDataType', 'Boolean', 'Indicates a boolean (0/1, true/false, etc) data type.  Note: This generalized data type, along with its size or maximum length, etc, will be translated into the corresponding implementation type in the data, service, and presentation layers, based on the implementation technologies utilized (e.g. PostgreSQL, Java, React, etc)', 'Eta Bool', '', -1, true, '00000000-0000-0000-0000-000000000000', '2f79fe7a822e7c6303edbeb4852d0cbd2bd8cd66f0737f9d70a7f9294256eaec', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (11, '1207c71d-6fbe-4baf-a5f3-d6db43576c49', 0, 'entitysubtype-entitytypeattribute-generalizeddatatype-integer', 2, 'GeneralizedDataType', 'Integer', 'Indicates an integer data type.  Note: This generalized data type, along with its size or maximum length, etc, will be translated into the corresponding implementation type in the data, service, and presentation layers, based on the implementation technologies utilized (e.g. PostgreSQL, Java, React, etc)', 'Eta Int', '', -1, true, '00000000-0000-0000-0000-000000000000', 'e137cf1aca6a7c5ec4abbca37a952e1456b4decf4b54f7055d1f696549feee1d', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (12, '91a000a4-a622-4786-9e35-efa4b3225109', 0, 'entitysubtype-entitytypeattribute-generalizeddatatype-unicodecharacter', 2, 'GeneralizedDataType', 'UnicodeCharacter', 'Indicates a single double-byte, Unicode character data type.  Note: This generalized data type, along with its size or maximum length, etc, will be translated into the corresponding implementation type in the data, service, and presentation layers, based on the implementation technologies utilized (e.g. PostgreSQL, Java, React, etc)', 'Eta Char', '', -1, true, '00000000-0000-0000-0000-000000000000', 'ecb7a624e6b273da2a9b687e6ce1a9e62a065be130740580ffaacd337218dbf5', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (13, 'f6215d7f-9a0e-465a-8f24-685b188b8763', 0, 'entitysubtype-entitytypeattribute-generalizeddatatype-unicodestring', 2, 'GeneralizedDataType', 'UnicodeString', 'Indicates a double-byte character string data type.  Note: This generalized data type, along with its size or maximum length, etc, will be translated into the corresponding implementation type in the data, service, and presentation layers, based on the implementation technologies utilized (e.g. PostgreSQL, Java, React, etc)', 'Eta Strg', '', -1, true, '00000000-0000-0000-0000-000000000000', 'e48f3984d18d425aed091271820e2128285e78fc2bffae4ac63da9b676cc39be', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (14, '3b8d5ba4-d391-4eed-b0f0-f2f6824b43ce', 0, 'entitysubtype-entitytypeattribute-generalizeddatatype-datetime', 2, 'GeneralizedDataType', 'DateTime', 'Indicates a date-time data type.  Note: This generalized data type, along with its size or maximum length, etc, will be translated into the corresponding implementation type in the data, service, and presentation layers, based on the implementation technologies utilized (e.g. PostgreSQL, Java, React, etc)', 'Eta DatTm', '', -1, true, '00000000-0000-0000-0000-000000000000', '9d11a75a495b0391ef6ac5f1edf1554d13590bd852c5969b3744132be7b8449d', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (16, 'edd4fdc7-506b-427c-a518-ca00f2018872', 0, 'entitysubtype-entitytypeattribute-generalizeddatatype-decimal', 2, 'GeneralizedDataType', 'Decimal', 'Indicates a decimal data type.  Note: This generalized data type, along with its size or maximum length, etc, will be translated into the corresponding implementation type in the data, service, and presentation layers, based on the implementation technologies utilized (e.g. PostgreSQL, Java, React, etc)', 'Eta Dec', '', -1, true, '00000000-0000-0000-0000-000000000000', '906d8ff1bc8c51e7c26b95bc7493dc1a27f766f85829623f808a5ad0e7638d37', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    @Getter private long DataSizeOrMaximumLengthInBytesOrCharacters;
    @Getter private long DataPrecision;
    @Getter private long DataScale;
    @Getter private long KeyTypeEntitySubtypeId;
    @Getter private long RelatedEntityTypeId;
    @Getter private long RelatedEntityTypeAttributeId;
    @Getter private long RelatedEntityTypeCardinalityEntitySubtypeId;
    @Getter private String EntitySubtypeGroupKey;
    @Getter private long EntityTypeAttributeValueEntitySubtypeId;
    //    (23, '9ebd5421-8fef-4214-b430-8d81b82aeda2', 0, 'entitysubtype-entitytypeattribute-value-primarykey', 2, 'Value', 'PrimaryKey', 'A system-generated integer value that uniquely identifies every row in a relational database table', 'Eta Val Prim', '', -1, true, '00000000-0000-0000-0000-000000000000', '1cc815ba648c4559d2f0d5e7fb67fdb24ca063ffac8809b06b9a6880724af0c5', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (24, 'eebc4c70-0d56-430f-8142-80bba3edc962', 0, 'entitysubtype-entitytypeattribute-value-uuid', 2, 'Value', 'Uuid', 'A system-generated universally unique identifier (UUID) value that uniquely identifies every row in a relational database table', 'Eta Val Uuid', '', -1, true, '00000000-0000-0000-0000-000000000000', 'be16698b9a67d0a2cc6887421eeb96eb9f20e30fa9c5785e78bc979a729c912c', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (25, '7b741ada-d5d8-46b2-b287-b492f698b510', 0, 'entitysubtype-entitytypeattribute-value-defaulttextkey', 2, 'Value', 'DefaultTextKey', 'A system-generated, human-readable string value that uniquely identifies every row in a relational database table', 'Eta Val TxtKy', '', -1, true, '00000000-0000-0000-0000-000000000000', 'f095a08777b2502e5a82f30142714cd6ee4bc6e70c5b8444ce534215b4841db1', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (26, '7c1a3dad-ada1-4f92-a063-276d17415532', 0, 'entitysubtype-entitytypeattribute-value-usedefault', 2, 'Value', 'UseDefault', 'A directive to utilize the statically-defined default value for this EntityTypeAttribute''s value', 'Eta Val Def', '', -1, true, '00000000-0000-0000-0000-000000000000', '2e0fd736941367d7ad49f112751fa8821c1a172141a3f6af595ad51647123a6a', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (27, 'cc2c1396-eaba-46ae-b72d-57f8840446f0', 0, 'entitysubtype-entitytypeattribute-value-createddatetime', 2, 'Value', 'CreatedDateTime', 'A system-generated datetime value that captures the date and time when this business entity instance was created', 'Eta Val Crtd', '', -1, true, '00000000-0000-0000-0000-000000000000', 'b1dd660f2b24d138ee7e141b5a68df30ec919dbdf89a8c6db41396efb79a8769', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (28, '700789df-4b03-4184-b6d8-ec5cbed03f15', 0, 'entitysubtype-entitytypeattribute-value-createdbyuserid', 2, 'Value', 'CreatedByUserId', 'A system-supplied integer value that identifies the InformationSystemUser that created this business entity instance', 'Eta Val CrtdBy', '', -1, true, '00000000-0000-0000-0000-000000000000', 'f85d083ed791e3def1cb291986cae34777e1f8403af7ff718fa85c75ad907fa2', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (29, '97abe62f-ba6e-433c-a6e8-51b31e8615b1', 0, 'entitysubtype-entitytypeattribute-value-updateddatetime', 2, 'Value', 'UpdatedDateTime', 'A system-generated datetime value that captures the date and time when this business entity instance was last updated', 'Eta Val Updtd', '', -1, true, '00000000-0000-0000-0000-000000000000', 'aabb02b756a2078e59dd142600b156fa28ab5216f488fd7fb4a4c2bc3d93f5cb', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (30, 'b4963521-62d9-4c9d-86d0-24db4f40e68e', 0, 'entitysubtype-entitytypeattribute-value-updatedbyuserid', 2, 'Value', 'UpdatedByUserId', 'A system-supplied integer value that identifies the InformationSystemUser that updated this business entity instance', 'Eta Val UpdtdBy', '', -1, true, '00000000-0000-0000-0000-000000000000', '6304f2edab277203b472d7ab5c99a5d3e6a114f75f033b0cb77712a3a3a2ba37', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (31, 'de81d568-a5af-4fa9-be0b-77dac2f076f6', 0, 'entitysubtype-entitytypeattribute-value-deleteddatetime', 2, 'Value', 'DeletedDateTime', 'A system-generated datetime value that captures the date and time when this business entity instance was marked for deletion', 'Eta Val Deltd', '', -1, true, '00000000-0000-0000-0000-000000000000', '74df3cd90bd2d51f5d2dc56546a08037a4248a74e489608a3697042760026729', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (32, 'f73b71f7-cdfc-4aca-b9a8-f8908cb4f15e', 0, 'entitysubtype-entitytypeattribute-value-deletedbyuserid', 2, 'Value', 'DeletedByUserId', 'A system-supplied integer value that identifies the InformationSystemUser that marked this business entity instance for deletion', 'Eta Val DeltdBy', '', -1, true, '00000000-0000-0000-0000-000000000000', 'f592b9222fcb278ecab1c83b7f17d669dedd0f75d53e65854b8a10ce13743661', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (33, '6878a60c-2e8b-422a-bbde-8baf8f189f7d', 0, 'entitysubtype-entitytypeattribute-value-correlationuuid', 2, 'Value', 'CorrelationUuid', 'A system-supplied universally unique identifier (UUID) value that uniquely identifies every row changed as the result of a single request', 'Eta Val Corrltn', '', -1, true, '00000000-0000-0000-0000-000000000000', '8ab5f886c8400a823b436e4554437aec286b14b612b7b3029037fbb372d7a80b', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    //    (34, 'e0a25e45-dd5b-4649-9fb7-f51f7c9a90c6', 0, 'entitysubtype-entitytypeattribute-value-digest', 2, 'Value', 'Digest', 'A system-generated cryptographic digest (or "hash") value that based on a business entity instance''s attribute values', 'Eta Val Dgst', '', -1, true, '00000000-0000-0000-0000-000000000000', 'c1f1609a20b5374521b05d1c866910f962442ba34430082098d3b2204965ffdf', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    @Getter private String DefaultValue;
    @Getter private String MinimumValue;
    @Getter private String MaximumValue;
    @Getter private String RegExValidationPattern;
    @Getter private float StepIncrementValue;
    @Getter private String RemoteValidationMethodAsAjaxUri;
    @Getter private long IndexEntitySubtypeId;
    @Getter private long UniquenessEntitySubtypeId;
    @Getter private long SensitivityEntitySubtypeId;
    @Getter private Timestamp PublishedAtDateTimeUtc;
    @Getter private long PublishedByInformationSystemUserId;

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

    public EntityTypeAttribute(long id, long entitySubtypeId, String textKey, String localizedName, String localizedInformation, String localizedPlaceholder, boolean isLocalizable, long generalizedDataTypeEntitySubtypeId, long dataSizeOrMaximumLengthInBytesOrCharacters, long dataPrecision, long dataScale, long keyTypeEntitySubtypeId, long relatedEntityTypeId, long relatedEntityTypeAttributeId, long relatedEntityTypeCardinalityEntitySubtypeId, String entitySubtypeGroupKey, long entityTypeAttributeValueEntitySubtypeId, String defaultValue, String minimumValue, String maximumValue, String regExValidationPattern, float stepIncrementValue, String remoteValidationMethodAsAjaxUri, long indexEntitySubtypeId, long uniquenessEntitySubtypeId, long sensitivityEntitySubtypeId, Timestamp publishedAtDateTimeUtc, long publishedByInformationSystemUserId, long ordinal, boolean isActive, Timestamp createdAtDateTimeUtc, long createdByInformationSystemUserId, Timestamp updatedAtDateTimeUtc, long updatedByInformationSystemUserId, Timestamp deletedAtDateTimeUtc, long deletedByInformationSystemUserId) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        LocalizedName = localizedName;
        LocalizedInformation = localizedInformation;
        LocalizedPlaceholder = localizedPlaceholder;
        IsLocalizable = isLocalizable;
        GeneralizedDataTypeEntitySubtypeId = generalizedDataTypeEntitySubtypeId;
        DataSizeOrMaximumLengthInBytesOrCharacters = dataSizeOrMaximumLengthInBytesOrCharacters;
        DataPrecision = dataPrecision;
        DataScale = dataScale;
        KeyTypeEntitySubtypeId = keyTypeEntitySubtypeId;
        RelatedEntityTypeId = relatedEntityTypeId;
        RelatedEntityTypeAttributeId = relatedEntityTypeAttributeId;
        RelatedEntityTypeCardinalityEntitySubtypeId = relatedEntityTypeCardinalityEntitySubtypeId;
        EntitySubtypeGroupKey = entitySubtypeGroupKey;
        EntityTypeAttributeValueEntitySubtypeId = entityTypeAttributeValueEntitySubtypeId;
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
