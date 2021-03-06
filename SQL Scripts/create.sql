CREATE TABLE users(
  nm_login VARCHAR(20) PRIMARY KEY,
  nm_pass VARCHAR(32) NOT NULL,
  nm_user VARCHAR(50) NOT NULL,
  fl_user SMALLINT NOT NULL
);

INSERT INTO users (
  nm_login,
  nm_pass,
  nm_user,
  fl_user
) VALUES (
  'admin',
  md5('admin'),
  'admin',
  3
);

CREATE SEQUENCE cd_software_seq;
CREATE TABLE software(
  cd_software BIGINT PRIMARY KEY DEFAULT NEXTVAL('cd_software_seq'),
  nm_software VARCHAR(50) NOT NULL,
  nu_release INT,
  nu_minor INT,
  nu_fix INT,
  fl_deprecate BOOLEAN DEFAULT FALSE
);
ALTER SEQUENCE cd_software_seq OWNED BY software.cd_software;

CREATE SEQUENCE cd_issue_seq;
CREATE TABLE issues(
  cd_issue BIGINT PRIMARY KEY DEFAULT NEXTVAL('cd_issue_seq'),
  nm_issue VARCHAR(20) NOT NULL,
  de_issue TEXT,
  fl_status SMALLINT DEFAULT 0,
  dt_create DATE DEFAULT current_date,
  dt_start DATE,
  dt_deadline DATE NOT NULL,
  dt_over DATE,
  nm_requester VARCHAR(20) NOT NULL REFERENCES users (nm_login),
  nm_approving VARCHAR(20) REFERENCES users (nm_login),
  nm_maker VARCHAR(20) REFERENCES users (nm_login),
  fl_category SMALLINT NOT NULL,
  cd_software BIGINT REFERENCES software (cd_software)
);
ALTER SEQUENCE cd_issue_seq OWNED BY issues.cd_issue;

CREATE SEQUENCE cd_comment_seq;
CREATE TABLE comment(
  cd_comment BIGINT PRIMARY KEY DEFAULT NEXTVAL('cd_comment_seq'),
  cd_issue BIGINT REFERENCES issues(cd_issue),
  nm_user VARCHAR(20) REFERENCES users (nm_login),
  de_comment TEXT,
  dt_comment DATE DEFAULT current_date
);
ALTER SEQUENCE cd_comment_seq OWNED BY comment.cd_comment;