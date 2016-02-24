SELECT H.mun,H.zone,H.brgy, aquanitype, COUNT(H.id) fishcount
FROM hpq_hh H, hpq_aquani A
WHERE H.id = A.hpq_hh_id AND aquanitype = 6
GROUP BY H.mun,H.zone,H.brgy,aquanitype
HAVING COUNT(H.id) > 0