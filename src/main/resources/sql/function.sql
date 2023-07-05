CREATE OR REPLACE FUNCTION MONTHS_BETWEEN (start_t date, end_t date)
    RETURNS INT AS $diff$
declare
    years INT = 0;
BEGIN
    years = DATE_PART('year', end_t) - DATE_PART('year', start_t);
RETURN years * 12 + (DATE_PART('month', end_t) - DATE_PART('month', start_t));
end;
$diff$ LANGUAGE plpgsql;
