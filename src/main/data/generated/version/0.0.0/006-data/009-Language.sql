-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: Language.Language

INSERT INTO "Language"."Language"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '6d8cd80e-2e48-43f3-ba7b-865ea939759f', 0, 'language-unknown', 'Unknown', 'Represents an as yet undetermined language value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Lang Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '967cf9d0a6831f8fa88a906da0ff7dd94c02203cc777cb11190c1a9d329295cb', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '5ccf79b3-cb67-4818-a99f-a23a9e50359e', 0, 'language-none', 'None', 'Represents a value indicating that language does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Lang None', '', -1, true, '00000000-0000-0000-0000-000000000000', '70803e7b64abb4fca64e03495b8d4f833e61aa9f8b9d4b6a7f26519c8ce3cff0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, 'd4a377af-23de-452b-ad1e-3b439d911bd9', 0, 'language-english', 'English', 'Indicates the written and spoken English language', 'en', '', -1, true, '00000000-0000-0000-0000-000000000000', '6c7ead2dd3be7a95dfc66dfaf1b6c7a9adbf79b5ed10aa09252e7272b3e58fdf', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;