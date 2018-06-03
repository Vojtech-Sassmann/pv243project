package cz.fi.muni.TACOS.service.Impl;

import java.util.Objects;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class Email {

    private String to;
    private String from;
    private String subject;
    private String body;

    private Email() {}

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private String to;
        private String from;
        private String subject;
        private String body;

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Email build() {
            Email e = new Email();
            e.to = this.to;
            e.body = this.body;
            e.subject = this.subject;
            e.from = this.from;
            return e;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(to, email.to) &&
                Objects.equals(from, email.from) &&
                Objects.equals(subject, email.subject) &&
                Objects.equals(body, email.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(to, from, subject, body);
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
