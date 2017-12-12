-- *****************************************************************************************
-- NACITA CLOB ZO SUBORU NA SERVERI
-- *****************************************************************************************
CREATE OR REPLACE FUNCTION get_local_ascii_data (p_dir IN VARCHAR2,
p_file IN VARCHAR2)
RETURN CLOB IS
-- ------------------------------------------------------------------------
l_bfile BFILE;
l_data CLOB;
src_csid NUMBER := NLS_CHARSET_ID('UTF8');
dest_offset INTEGER := 1;
src_offset INTEGER := 1;
lang_context INTEGER := dbms_lob.default_lang_ctx;
warning INTEGER;
BEGIN
DBMS_LOB.createtemporary (lob_loc => l_data,
cache => TRUE,
dur => DBMS_LOB.call);
l_bfile := BFILENAME(p_dir, p_file);
DBMS_LOB.fileopen(l_bfile, DBMS_LOB.file_readonly);
DBMS_LOB.loadclobfromfile(l_data, l_bfile, DBMS_LOB.getlength(l_bfile),
dest_offset,src_offset, src_csid, lang_context, warning);
DBMS_LOB.fileclose(l_bfile);
RETURN l_data;
END;
/

-- ****************************************************************************************
-- ULOZI CLOB DO SUBORU NA SERVERI
-- ****************************************************************************************
CREATE OR REPLACE PROCEDURE put_local_ascii_data (
p_data IN CLOB
, p_dir IN VARCHAR2
, p_file IN VARCHAR2
)
IS
-- -------------------------------------------------------------------------
l_out_file UTL_FILE.file_type;
l_buffer VARCHAR2 (32767);
l_amount BINARY_INTEGER := 32767;
l_pos INTEGER := 1;
l_clob_len INTEGER;
BEGIN
l_clob_len:= DBMS_LOB.getlength (p_data);
l_out_file:=UTL_FILE.fopen (p_dir,p_file,'w',32767);
WHILE l_pos < l_clob_len
LOOP
DBMS_LOB.READ (p_data,l_amount,l_pos,l_buffer);
IF l_buffer IS NOT NULL
THEN
UTL_FILE.put (l_out_file, l_buffer);
UTL_FILE.fflush (l_out_file);
END IF;
l_pos := l_pos+ l_amount;
END LOOP;
UTL_FILE.fclose (l_out_file);
EXCEPTION
WHEN OTHERS
THEN
IF UTL_FILE.is_open (l_out_file)
THEN
UTL_FILE.fclose (l_out_file);
END IF;
DBMS_OUTPUT.put_line ( SQLCODE|| ' - '|| SQLERRM);
RAISE;
END;
/

-- *****************************************************************************************
-- ULOZI BLOB DO SUBORU NA SERVERI
-- *****************************************************************************************
CREATE OR REPLACE PROCEDURE put_local_binary_data (
p_data IN BLOB,
p_dir IN VARCHAR2,
p_file IN VARCHAR2
) IS
-- -----------------------------------------------------------------------------------------
l_out_file UTL_FILE.file_type;
l_buffer RAW (32767);
l_amount BINARY_INTEGER := 32767;
l_pos INTEGER := 1;
l_blob_len INTEGER;
BEGIN
l_blob_len := DBMS_LOB.getlength(p_data);
l_out_file := UTL_FILE.fopen(p_dir, p_file, 'w', 32767);
WHILE l_pos < l_blob_len
LOOP
DBMS_LOB.READ(p_data, l_amount, l_pos, l_buffer);
IF l_buffer IS NOT NULL THEN
UTL_FILE.put_raw(l_out_file, l_buffer, TRUE);
END IF;
l_pos := l_pos+l_amount;
END LOOP;
EXCEPTION
WHEN OTHERS THEN
IF UTL_FILE.is_open(l_out_file) THEN
UTL_FILE.fclose(l_out_file);
END IF;
RAISE;
END put_local_binary_data;
/

-- *****************************************************************************************
-- VYROB BLOB ZO SUBORU NA SERVERI
-- *****************************************************************************************
CREATE OR REPLACE FUNCTION get_local_binary_data(
p_dir IN VARCHAR2,
p_file IN VARCHAR2
) RETURN BLOB
IS
-- -----------------------------------------------------------------------------------------
l_amount PLS_INTEGER;
l_buffer RAW (32767);
l_data BLOB;
l_out_file UTL_FILE.file_type;
l_length NUMBER;
v_dest_offset NUMBER(38):= 1 ;
v_src_offset NUMBER(38):= 1 ;
v_buf_size CONSTANT BINARY_INTEGER := 32767;
v_write_amount BINARY_INTEGER;
v_total_amount BINARY_INTEGER :=0 ;
BEGIN
DBMS_LOB.createtemporary (lob_loc => l_data, CACHE => true,
dur => DBMS_LOB.CALL);
l_out_file := UTL_FILE.fopen(LOCATION=> p_dir, filename => p_file,
open_mode => 'r', max_linesize => v_buf_size);
BEGIN
v_dest_offset := 1;
WHILE (TRUE) LOOP
UTL_FILE.get_raw(file => l_out_file, buffer => l_buffer,
len => v_buf_size);
IF l_buffer IS NOT NULL THEN
v_write_amount := LENGTH (l_buffer)/2; -- sanity check
v_total_amount := v_total_amount + v_write_amount;
ELSE
-- RAISE get_raw_read_null_bytes;
RAISE_APPLICATION_ERROR(-20000,'XXXX');
END IF;
DBMS_LOB.WRITE(lob_loc=> l_data, amount => v_write_amount,
offset => v_dest_offset, buffer => l_buffer);
v_dest_offset := v_dest_offset + v_write_amount;
END LOOP;
EXCEPTION
WHEN NO_DATA_FOUND THEN
NULL;
END;
UTL_FILE.fclose(file => l_out_file);
RETURN l_data;
END get_local_binary_data;
/
