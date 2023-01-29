-- FUNCTION: CreateRowDigest

-- DROP FUNCTION CreateRowDigest ;

CREATE FUNCTION CreateRowDigest(rowValues bytea)
    RETURNS character varying(500)
    LANGUAGE plpgsql
AS
$$
DECLARE
    rowDigest character varying(500);
BEGIN
    SELECT sha256(rowValues) INTO rowDigest;
    RETURN rowDigest;
END;
$$
