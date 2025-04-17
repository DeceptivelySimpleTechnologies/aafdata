-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: Currency.Currency

-- DROP TABLE "Currency"."Currency";

CREATE TABLE "Currency"."Currency"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "CountryGeographicUnitId" bigint NOT NULL,
    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Ordinal" bigint NOT NULL DEFAULT -1,
    "IsActive" boolean NOT NULL DEFAULT true,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "Currency_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "Currency_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "Currency_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "Currency_UQ1_CountryGeoUntId_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("CountryGeographicUnitId", "LocalizedName", "DeletedAtDateTimeUtc"),

    CONSTRAINT "Currency_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "Currency_FK_CountryGeoUntId" FOREIGN KEY ("CountryGeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "Currency_IDX_LocalizedName" ON "Currency"."Currency" ("LocalizedName")
