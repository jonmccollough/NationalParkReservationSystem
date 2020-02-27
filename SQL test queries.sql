SELECT site_id FROM site 
WHERE (campground_id = 1);

SELECT site_id FROM campground 
JOIN site USING(campground_id) 
LEFT JOIN reservation USING (site_id)
WHERE campground_id = 1 
AND ('2020-02-20' BETWEEN from_date AND to_date
OR '2020-02-21' BETWEEN from_date AND to_date
OR ('2020-02-20' < from_date AND '2020-02-21' > to_date))
GROUP BY site_id;


SELECT site_id, site_number, daily_fee FROM campground
JOIN site USING (campground_id)
LEFT JOIN reservation USING (site_id)
WHERE campground_id =1 AND site_id 
NOT IN (
SELECT site_id FROM campground 
JOIN site USING(campground_id) 
LEFT JOIN reservation USING (site_id)
WHERE campground_id = 1 
AND ('2020-02-20' BETWEEN from_date AND to_date
OR '2020-02-21' BETWEEN from_date AND to_date
OR ('2020-02-20' < from_date AND '2020-02-21' > to_date)))
GROUP BY site_id, site_number, daily_fee
LIMIT 5;

SELECT * FROM reservation
WHERE reservation_id = 1;

INSERT INTO park VALUES (DEFAULT, 'TESTPARKNAME', 'TESTPARKLOC', '2222-01-01', 1, 1, 'TESTPARKDESC');
INSERT INTO campground VALUES (DEFAULT, (SELECT park_id FROM park WHERE name = 'TESTPARKNAME'), 'TESTCAMPNAME', '13', '14', 10);
INSERT INTO site VALUES (DEFAULT, (SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME'), 1, 5, false, 10, false);

DELETE FROM site WHERE site_id = 623;
DELETE FROM campground WHERE name = 'TESTCAMPNAME';
DELETE FROM park WHERE name = 'TESTPARKNAME';

SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME';




 
