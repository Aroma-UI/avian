package java.text;

import java.util.Date;

public abstract class DateFormat extends Format {

    public final String format(Date date) {
        return format(date, new StringBuffer(), null).toString();
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof Date)
            return format((Date) obj, toAppendTo, pos);
        else if (obj instanceof Number)
            return format(new Date(((Number) obj).longValue()), toAppendTo, pos);
        else
            throw new IllegalArgumentException("Object " + (obj == null ? "null" : obj.getClass().getName()) + " can not be formatted as date");
    }

    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    public abstract StringBuffer format(Date date, StringBuffer buffer, FieldPosition position);

    public abstract Date parse(String text, ParsePosition position);
}
