-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: Person.Person

-- DROP TABLE "Person"."Person";

CREATE TABLE "Person"."Person"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LegalGivenName1" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LegalGivenName2" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "LegalGivenName3" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "LegalSurname1" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LegalSurname2" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "LegalSurname3" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "BornAtDateTimeUtc" timestamp without time zone NOT NULL,
    "LegalCitizenOfCountryGeographicUnitId" bigint NOT NULL,
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

    CONSTRAINT "Person_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "Person_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "Person_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "Person_UQ1_EntSubId_Nam1_Nam2_Nam3_Sur1_Sur2_Sur3_BornAt_CountryId_DeletedAt" UNIQUE ("EntitySubtypeId", "LegalGivenName1", "LegalSurname1", "BornAtDateTimeUtc", "LegalCitizenOfCountryGeographicUnitId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "Person_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "Person_FK_LegalCitizenOfCountryGeographicUnitId" FOREIGN KEY ("LegalCitizenOfCountryGeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id"),
    CONSTRAINT "Person_FK_LocaleId" FOREIGN KEY ("LocaleId") REFERENCES "Locale"."Locale"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "Person_IDX_LegalSurname1_LegalGivenName1" ON "Person"."Person" ("LegalSurname1", "LegalGivenName1")
