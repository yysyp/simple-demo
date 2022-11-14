
create index code_year_month on new_stock_data (company_code, period_year, period_month);
create index code_year_month_kemu on new_stock_data (company_code, period_year, period_month, kemu);
create index code_year_month_kemuType on new_stock_data (company_code, period_year, period_month, kemu_type);
create index code_year_month_yoy on new_stock_data (company_code, period_year, period_month, yoy);

