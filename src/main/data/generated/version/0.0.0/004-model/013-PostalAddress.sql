-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: PostalAddress.PostalAddress

-- DROP TABLE "PostalAddress"."PostalAddress";

CREATE TABLE "PostalAddress"."PostalAddress"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "StreetAddress1" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "StreetAddress2" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "StreetAddress3" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "CityName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Subdivision1GeographicUnitId" bigint NOT NULL,
    "Subdivision2GeographicUnitId" bigint NOT NULL DEFAULT 0,
    "Subdivision3GeographicUnitId" bigint NOT NULL DEFAULT 0,
    "CountryGeographicUnitId" bigint NOT NULL,
    "PostalCode" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "Latitude" numeric(8,6) NOT NULL,
    "Longitude" numeric(9,6) NOT NULL,

    "LocaleId" bigint NOT NULL,

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

    CONSTRAINT "PostalAddress_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "PostalAddress_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "PostalAddress_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "PostalAddress_UQ1_Adr1_Adr2_Adr3_CityName_Sub1Id_Sub2Id_Sub3Id_CountryId_PostalCode_DeletedAt" UNIQUE ("StreetAddress1", "StreetAddress2", "StreetAddress3", "CityName", "Subdivision1GeographicUnitId", "Subdivision2GeographicUnitId", "Subdivision3GeographicUnitId", "CountryGeographicUnitId", "PostalCode", "DeletedAtDateTimeUtc"),

    CONSTRAINT "PostalAddress_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "PostalAddress_FK_Subdivision1GeographicUnitId" FOREIGN KEY ("Subdivision1GeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id"),
    CONSTRAINT "PostalAddress_FK_Subdivision2GeographicUnitId" FOREIGN KEY ("Subdivision2GeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id"),
    CONSTRAINT "PostalAddress_FK_Subdivision3GeographicUnitId" FOREIGN KEY ("Subdivision3GeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id"),
    CONSTRAINT "PostalAddress_FK_CountryGeographicUnitId" FOREIGN KEY ("CountryGeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "PostalAddress_IDX_StreetAddress1" ON "PostalAddress"."PostalAddress" ("StreetAddress1");
CREATE INDEX "PostalAddress_IDX_CityName" ON "PostalAddress"."PostalAddress" ("CityName");
CREATE INDEX "PostalAddress_IDX_Subdivision1GeographicUnitId" ON "PostalAddress"."PostalAddress" ("Subdivision1GeographicUnitId");
CREATE INDEX "PostalAddress_IDX_CountryGeographicUnitId" ON "PostalAddress"."PostalAddress" ("CountryGeographicUnitId");
CREATE INDEX "PostalAddress_IDX_PostalCode" ON "PostalAddress"."PostalAddress" ("PostalCode")
