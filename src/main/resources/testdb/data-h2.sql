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
select id, '면접을 너무 잘 봤다', 'good', 'bad', '좋아', 5, now(), now() from stage limit 3;

insert into document(job_id, content, created_at, last_modified_at)
    select id, '아주 멋진 입사지원서와 자기 소개서', now(), now() from job limit 2;
