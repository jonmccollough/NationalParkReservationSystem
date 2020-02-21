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



 
