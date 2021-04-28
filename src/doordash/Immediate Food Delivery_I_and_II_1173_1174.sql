

--Leetcode 1173
-- Write an SQL query to find the percentage of immediate orders in the table, rounded to 2 decimal places.

--https://github.com/angelsophia1/LeetCode-Locked/blob/master/1173%09%20Immediate%20Food%20Delivery%20I

-- Below from https://www.programmersought.com/article/54055964917/
select round(sum(order_date = customer_pref_delivery_date)*100/count(delivery_id),2) as immediate_percentage 
from Delivery;


--Leetcode 1174
--Write an SQL query to find the percentage of immediate orders in the first orders of all customers, 
--rounded to 2 decimal places.

-- Below from https://www.programmersought.com/article/54055964917/
select round (sum(order_date = customer_pref_delivery_date) * 100 /count(*),2) as immediate_percentage
from Delivery
where (customer_id, order_date) in (
    select customer_id, min(order_date)
    from delivery
    group by customer_id
)




