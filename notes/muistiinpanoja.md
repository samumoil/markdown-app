     if (Character.isAlphabetic(iterator.current())) {
                // Jos läpikäytävä merkki on kirjain, lisätään se sanaan.
                word.append(comparisonString);
                if (word.isEmpty()) {
                    // Jos tämä on uuden sanan alku, lisätään sanalaskuria.
                    words++;
                }
            } else if (!word.isEmpty()) {
                // Eli aiemmin on kohdattu aakkonen ja sana on aloitettu. Nyt kohdataan erikoismerkki.
                if (Character.isWhitespace(iterator.current())) {
                    // Jos merkki on välilyönti tai muu vastaava, sana loppuu varmasti. Tyhjennetään word.
                    word.setLength(0);
                    Character.is
                    specialCharacterEncountered = null;
                } else if (specialCharacterEncountered == null) {
                    // Jos yhtään erikoismerkkiä ei ole vielä kohdattu, lisätään merkki sanaan ja merkitään erikoismerkki.
                    word.append(comparisonString);
                    specialCharacterEncountered = comparisonString;
                } else if (comparisonString.equals(specialCharacterEncountered) &&
                        !comparisonString.equals(word.substring(word.length() - 1))) {
                    // Jos kohdattu erikoismerkki on sama kuin aiemmin kohdattu ja se on eri merkki kuin edellinen
                    // sanaan lisätty merkki, lisätään kohdattu merkki sanaan.
                } else {
                    // Jos kohdataan mikä tahansa muu erikoismerkki, sana päättyy.
                    word.setLength(0);
                    specialCharacterEncountered = null;
                }
            } else {
                // Muissa tapauksissa ei tapahdu mitään. Nollataan mittarit.
                word.setLength(0);
                specialCharacterEncountered = null;
            }
        }
