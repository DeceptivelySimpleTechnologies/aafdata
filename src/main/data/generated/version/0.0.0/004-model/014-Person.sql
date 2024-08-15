-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: Person.Person

-- DROP TABLE "Person"."Person";

CREATE TABLE "Person"."Person"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LegalGivenName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LegalSurname" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "BornAtDateTimeUtc" timestamp without time zone NOT NULL,
    "LegalCitizenOfCountryGeographicUnitId" bigint NOT NULL,
    "LocaleId" bigint NOT NULL,

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

    CONSTRAINT "Person_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "Person_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "Person_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "Person_UQ1_EntitySubtypeId_LegalGivenName_LegalSurname_BornAtDateTimeUtc_LegalCitizenOfCountryGeographicUnitId_DeletedAtDateTimeUtc" UNIQUE ("EntitySubtypeId", "LegalGivenName", "LegalSurname", "BornAtDateTimeUtc", "LegalCitizenOfCountryGeographicUnitId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "Person_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "Person_FK_LegalCitizenOfCountryGeographicUnitId" FOREIGN KEY ("LegalCitizenOfCountryGeographicUnitId") REFERENCES "GeographicUnit"."GeographicUnit"("Id"),
    CONSTRAINT "Person_FK_LocaleId" FOREIGN KEY ("LocaleId") REFERENCES "Locale"."Locale"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "Person_IDX_LegalSurname_LegalGivenName" ON "Person"."Person" ("LegalSurname", "LegalGivenName")
