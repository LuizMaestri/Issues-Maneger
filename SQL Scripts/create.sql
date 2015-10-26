CREATE TABLE users(
  nm_login VARCHAR(20) PRIMARY KEY,
  nm_pass VARCHAR(32) NOT NULL,
  nm_user VARCHAR(50) NOT NULL,
  fl_approve BOOLEAN,
  fl_admin BOOLEAN
);

INSERT INTO users (
  nm_login,
  nm_pass,
  nm_user,
  fl_admin
) VALUES (
  'admin',
  md5('admin'),
  'admin',
  TRUE
);

CREATE SEQUENCE cd_issue_seq;
CREATE TABLE issues(
  cd_issue INT PRIMARY KEY DEFAULT NEXTVAL('cd_issue_seq'),
  nm_issue VARCHAR(20) NOT NULL ,
  de_issue TEXT,
  fl_status SMALLINT DEFAULT 0,
  dt_start DATE DEFAULT current_date,
  dt_deadline DATE,
  dt_over DATE,
  nm_requester VARCHAR(20) NOT NULL REFERENCES users (nm_login),
  nm_approving VARCHAR(20) REFERENCES users (nm_login),
  nm_maker VARCHAR(20) REFERENCES users (nm_login),
  fl_category SMALLINT NOT NULL
);
ALTER SEQUENCE cd_issue_seq OWNED BY issues.cd_issue;