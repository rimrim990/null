insert into company(name, region, series, created_at, last_modified_at)
    values('Google', 'SEOUL', 'A', now(), now());
insert into company(name, region, series, created_at, last_modified_at)
    values('Netflix', 'ETC', 'IPO', now(), now());
insert into company(name, region, series, created_at, last_modified_at)
    values('Amazon', 'SEOUL', 'D', now(), now());

insert into job(company_id, position, created_at, last_modified_at)
    select id, '백엔드', now(), now() from company;

insert into stage(job_id, name, state, created_at, last_modified_at)
    select id, '서류', 'PASS', now(), now() from job;
insert into stage(job_id, name, state, created_at, last_modified_at)
    select id, '코딩 테스트', 'PASS', now(), now() from job;
insert into stage(job_id, name, state, created_at, last_modified_at)
    select id, '1차 면접', 'WAIT', now(), now() from job;

insert into retrospect(stage_id, content, good_point, bad_point, summary, score, created_at, last_modified_at)
    select id, 'test', 'good', 'bad', 'summary', 3, now(), now() from stage;
