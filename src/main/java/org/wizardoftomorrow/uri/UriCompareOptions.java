package org.wizardoftomorrow.uri;

/**
 *
 * @param schemaCaseSensitive
 * @param schemaRequired
 * @param hostCaseSensitive
 * @param hostRequired
 * @param pathCaseSensitive
 * @param pathRequired
 * @param queryStringKeysCaseSensitive
 * @param queryStringRequired
 * @param queryStringValuesCaseSensitive
 *
 * @author Marko Vučković
 */
public record UriCompareOptions(boolean schemaCaseSensitive,
                                boolean schemaRequired,
                                boolean hostCaseSensitive,
                                boolean hostRequired,
                                boolean pathCaseSensitive,
                                boolean pathRequired,
                                boolean queryStringKeysCaseSensitive,
                                boolean queryStringRequired,
                                boolean queryStringValuesCaseSensitive) {

  /**
   *
   */
  public static final UriCompareOptions DEFAULT = new Builder()
      .schemaCaseSensitive(false)
      .schemaRequired(false)
      .hostCaseSensitive(false)
      .hostRequired(true)
      .pathCaseSensitive(false)
      .pathRequired(true)
      .queryStringKeysCaseSensitive(true)
      .queryStringRequired(true)
      .queryStringValuesCaseSensitive(false)
      .build();

  /**
   *
   * @author Marko Vučković
   */
  public static class Builder {

    boolean schemaCaseSensitive;
    boolean schemaRequired;
    boolean hostCaseSensitive;
    boolean hostRequired;
    boolean pathCaseSensitive;
    boolean pathRequired;
    boolean queryStringKeysCaseSensitive;
    boolean queryStringRequired;
    boolean queryStringValuesCaseSensitive;

    /**
     *
     * @param value
     * @return
     */
    public Builder schemaCaseSensitive(boolean value) {
      this.schemaCaseSensitive = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder schemaRequired(boolean value) {
      this.schemaRequired = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder hostCaseSensitive(boolean value) {
      this.hostCaseSensitive = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder hostRequired(boolean value) {
      this.hostRequired = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder pathCaseSensitive(boolean value) {
      this.pathCaseSensitive = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder pathRequired(boolean value) {
      this.pathRequired = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder queryStringKeysCaseSensitive(boolean value) {
      this.queryStringKeysCaseSensitive = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder queryStringRequired(boolean value) {
      this.queryStringRequired = value;
      return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public Builder queryStringValuesCaseSensitive(boolean value) {
      this.queryStringValuesCaseSensitive = value;
      return this;
    }

    /**
     *
     * @return
     */
    public UriCompareOptions build() {
      return new UriCompareOptions(
          schemaCaseSensitive,
          schemaRequired,
          hostCaseSensitive,
          hostRequired,
          pathCaseSensitive,
          pathRequired,
          queryStringKeysCaseSensitive,
          queryStringRequired,
          queryStringValuesCaseSensitive);
    }

  }

}
