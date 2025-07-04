-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: TelephoneNumber.TelephoneNumber

-- DROP TABLE "TelephoneNumber"."TelephoneNumber";

CREATE TABLE "TelephoneNumber"."TelephoneNumber"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,
    "CountryCode" character varying(3) COLLATE pg_catalog."default" NOT NULL,
    "SubscriberNumber" character varying(12) COLLATE pg_catalog."default" NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Ordinal" bigint NOT NULL DEFAULT '-1',
    "IsActive" boolean NOT NULL DEFAULT true,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "TelephoneNumber_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "TelephoneNumber_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "TelephoneNumber_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "TelephoneNumber_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc"),
    CONSTRAINT "TelephoneNumber_UQ1_CountryCode_SubscriberNumber_DeletedAtDateTimeUtc" UNIQUE ("CountryCode", "SubscriberNumber", "DeletedAtDateTimeUtc"),

    CONSTRAINT "TelephoneNumber_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "TelephoneNumber_IDX_LocalizedName" ON "TelephoneNumber"."TelephoneNumber" ("LocalizedName");
CREATE INDEX "TelephoneNumber_IDX_CountryCode" ON "TelephoneNumber"."TelephoneNumber" ("CountryCode");
CREATE INDEX "TelephoneNumber_IDX_SubscriberNumber" ON "TelephoneNumber"."TelephoneNumber" ("SubscriberNumber");
