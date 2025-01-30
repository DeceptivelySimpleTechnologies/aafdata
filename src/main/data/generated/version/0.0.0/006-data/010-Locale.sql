-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: Locale.Locale

INSERT INTO "Locale"."Locale"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LanguageId", "GeographicUnitId", "StandardizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '4e8e579c-8634-491a-88cf-8ca1787134ca', 0, 'locale-unknown', -1, -1, 'Unknown', 'Represents an as yet undetermined locale value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Loc Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '7934557e76cb304b54e4cda6b77058818b7600e0329a7f7a43f4c05640180627', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, 'f0a576df-e340-47c9-a969-4d0a587a29ae', 0, 'locale-none', 0, 0, 'None', 'Represents a value indicating that locale does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Loc None', '', -1, true, '00000000-0000-0000-0000-000000000000', '3c9372e9ab61ca08d567ce606411c194e13df092c6d8ce302c3e513401f947f0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, '80464090-3962-4bca-b387-ffe0150e6367', 0, 'locale-us-english', 1, 1, 'U.S. English', 'Indicates the English language as written and spoken in the United States of America, as well as U.S. date/time formats, currency, units of measure, etc', 'en-us', '', -1, true, '00000000-0000-0000-0000-000000000000', 'ce8b96fb9371413d534d64b148afe020661ed0fc48e44c30f5f74df35332e302', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;