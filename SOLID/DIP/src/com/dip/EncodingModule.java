package com.dip;

import java.io.IOException;
import java.util.Base64;

import DIP.IReader;
import com.dip.IWriter;

public class EncodingModule
{
    IReader reader= null;
    IWriter writer = null;

    public EncodingModule(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }


    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public void encode() throws IOException
    {
        String aLine = this.reader.read();
        String encodedLine = Base64.getEncoder().encodeToString(aLine.getBytes());
        this.writer.write(encodedLine);
    }
}

