package si.lista4.model;

public class Record {
    private final String className;
    private final String articleContent;

    public Record(String className, String articleContent) {
        this.className = className;
        this.articleContent = articleContent;
    }

    public String getClassName() {
        return className;
    }

    public String getArticleContent() {
        return articleContent;
    }
}
