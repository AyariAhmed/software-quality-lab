package com.dip;

import java.io.IOException;
import java.util.Base64;

import DIP.IReader;
import com.dip.IWriter;

public class EncodingModule
{
    public void encode(IReader reader, IWriter writer) throws IOException
    {
        String aLine = reader.read();
        String encodedLine = Base64.getEncoder().encodeToString(aLine.getBytes());
        writer.write(encodedLine);
    }
}

