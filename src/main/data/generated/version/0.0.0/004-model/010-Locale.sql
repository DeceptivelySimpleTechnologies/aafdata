-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: Locale.Locale

-- DROP TABLE "Locale"."Locale";

CREATE TABLE "Locale"."Locale"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LanguageId" bigint NOT NULL,
    "GeographicUnitId" bigint NOT NULL,
    "StandardizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Ordinal" bigint NOT NULL,
    "IsActive" boolean NOT NULL,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "Locale_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "Locale_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),
    CONSTRAINT "Locale_CHK_StandardizedName" CHECK ("StandardizedName" ~* '^[A-Za-z,\.,!?\/ ]+$'),

    CONSTRAINT "Locale_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "Locale_UQ1_StandardizedName_DeletedAtDateTimeUtc" UNIQUE ("StandardizedName", "DeletedAtDateTimeUtc"),

    CONSTRAINT "Locale_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "Locale_FK_LanguageId" FOREIGN KEY ("LanguageId") REFERENCES "Language"."Language"("Id"),
    CONSTRAINT "Locale_FK_GeographicUnitId" FOREIGN KEY ("GeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "Locale_IDX_StandardizedName" ON "Locale"."Locale" ("StandardizedName")
