CREATE INDEX HIndex
ON db_hpq.hpq_hh (nnucfam);

SELECT mun, zone, brgy, purok
		, SUM(nnucfam) AS `Nuclear Families`, SUM(nofw) AS OFWs
        , SUM(nofw) / SUM(nnucfam) AS `Average OFW's per Nuclear Family`
FROM (SELECT mun, zone, brgy, purok, nnucfam, nofw
		FROM db_hpq.hpq_hh
		WHERE nnucfam > 0) A
GROUP BY mun, zone, brgy, purok
HAVING SUM(nofw) > 0;

ALTER TABLE hpq_hh DROP INDEX HIndex;
