package com.example.hbasestudy.controller;

import javax.activation.DataSource;
import java.io.*;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description ces
 * @date 2020/7/13 18:10
 */
public class ByteArrayDataSource implements DataSource {
    /*** Data to write. */
    private byte[] _data;
    /*** Content-Type. */
    private String _type;

    /**
     * Create a datasource from an input stream */
    public ByteArrayDataSource(InputStream is, String type) {
        _type = type;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1){
                os.write(ch);
            }
            _data = os.toByteArray();
        } catch (IOException ioe) {
        }
    }

    /** Create a datasource from a byte array */
    public ByteArrayDataSource(byte[] data, String type) {
        _data = data;
        _type = type;
    }

    /** Create a datasource from a String */
    public ByteArrayDataSource(String data, String type) {
        try {
            _data = data.getBytes("iso-8859-1");
        } catch (UnsupportedEncodingException uee) {
        }
        _type = type;
    }

    @Override
    public InputStream getInputStream()
            throws IOException {
        if (_data == null)
            throw new IOException("no data");
        return new ByteArrayInputStream(_data);
    }

    @Override
    public OutputStream getOutputStream()
            throws IOException {
        throw new IOException("cannot do this");
    }

    @Override
    public String getContentType() {
        return _type;
    }

    @Override
    public String getName() {
        return "dummy";
    }
}
