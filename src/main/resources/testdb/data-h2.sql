insert into company(name, region, series, created_at, last_modified_at)
    values('Google', 'Seoul', 'A', now(), now());
insert into company(name, region, series, created_at, last_modified_at)
    values('Netflix', 'ETC', 'IPO', now(), now());
insert into company(name, region, series, created_at, last_modified_at)
    values('Amazon', 'Seoul', 'D', now(), now());

insert into job(company_id, position, created_at, last_modified_at)
    select id, '백엔드', now(), now() from company;

insert into stage(job_id, name, state, created_at, last_modified_at)
    select id, '서류', 'PASS', now(), now() from job;
insert into stage(job_id, name, state, created_at, last_modified_at)
    select id, '코딩 테스트', 'PASS', now(), now() from job;
insert into stage(job_id, name, state, created_at, last_modified_at)
    select id, '1차 면접', 'WAIT', now(), now() from job;
