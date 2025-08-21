-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: KeywordGroup.KeywordGroup

INSERT INTO "KeywordGroup"."KeywordGroup"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, 'b04a7922-c4af-46b1-8157-3615df137bcf', 0, 'keywordgroup-unknown', 'Unknown', 'Represents an as yet undetermined KeywordGroup value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'KywdGrp Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '2d535c8eac51947cd4715b4b71df81f1535261f89624968d1d077a0491cd69e0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '95d0e47e-9497-4189-b4f8-c5212f8fe4b7', 0, 'keywordgroup-none', 'None', 'Represents a value indicating that KeywordGroup does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'KywdGrp None', '', -1, true, '00000000-0000-0000-0000-000000000000', '76e4cd6e1b41be4be34a7ebb820abe2f479e58a5672aca83a3210f8bc923333a', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
