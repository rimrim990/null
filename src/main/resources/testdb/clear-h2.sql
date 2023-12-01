set foreign_key_checks=0;

select concat('truncate table ',  table_name, ':')
from information_schema.TABLES
where TABLE_SCHEMA = 'db_name'
and TABLE_TYPE = 'base table';

set foreign_key_checks=1
