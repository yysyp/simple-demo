

-- Implement your solution here
select g.name, IFNULL(t.all_test_cases, 0), IFNULL(t.passed_test_cases, 0), IFNULL(g.test_value*t.passed_test_cases,0) as total_value from test_groups g left join (

select tc.group_name, count(status) all_test_cases, sum(case when status='OK' then 1 else 0 end) as passed_test_cases from test_cases tc group by tc.group_name

) t on g.name = t.group_name order by total_value desc, group_name asc;

