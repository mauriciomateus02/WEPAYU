package br.ufal.ic.p2.wepayu.middleware.serviceDatabese;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class ValidatorOfEntity {

    protected static void setEntities(String entitie) throws Exception {
        try {
            String[] objEntitie;

            if (entitie.contains("->")) {

                String[] objs = entitie.split("->");

                // devide o objeto para verificar e cada valor do array representa uma coisa
                objEntitie = objs[0].split(";");

                if (objs[1].length() > 2) {

                    // cria a lista de infromações sobre compra ou horas trabalhadas, ou taxas de
                    // serviços
                    String[] list;

                    // objs[1] é um array de valores que pode ser horista ou comissiondo
                    objs[1] = Conversor.converterArray(objs[1]);

                    // dividi o array em pelas virgulas
                    list = objs[1].split(",");

                    // existe o padrão que se o primeiro objeto começa com s significa que é do tipo
                    // sindicalizado
                    if (objEntitie[0].contains("s")) {
                        DataRetriever.setUnionized(objEntitie[1], objEntitie[2], objEntitie[3], list);
                    } else if (objEntitie[3].equals("horista")) {
                        // como o tamanho é 5 significa que ele pode ser assalariado ou horista
                        objEntitie[5] = Conversor.converterInvertedCharacter(objEntitie[5]);
                        DataRetriever.setEmployee(objEntitie[0], objEntitie[1], objEntitie[2], objEntitie[3],
                                objEntitie[4],
                                Float.parseFloat(objEntitie[5]), objEntitie[6],
                                list);
                    } else if (objEntitie[3].equals("comissionado")) {
                        // como o tamanho é 6 significa que é comissionado.
                        objEntitie[5] = Conversor.converterInvertedCharacter(objEntitie[5]);
                        objEntitie[6] = Conversor.converterInvertedCharacter(objEntitie[6]);

                        DataRetriever.setEmployee(objEntitie[0], objEntitie[1], objEntitie[2], objEntitie[3],
                                objEntitie[4],
                                Float.parseFloat(objEntitie[5]),
                                Float.parseFloat(objEntitie[6]), objEntitie[7], list);
                    }

                }

                else {

                    if (objEntitie[0].contains("s")) {

                        // como é usado no employee cada objs represneta uma informação da class
                        DataRetriever.setUnionized(objEntitie[1], objEntitie[2], objEntitie[3]);

                    } else if (objEntitie[3].equals("horista")) {
                        // converte a virgula em ponto ex.: 20,00 em 20.00
                        if (objEntitie[5].contains(","))
                            objEntitie[5] = Conversor.converterInvertedCharacter(objEntitie[5]);

                        DataRetriever.setEmployee(objEntitie[0], objEntitie[1], objEntitie[2], objEntitie[3],
                                objEntitie[4],
                                Float.parseFloat(objEntitie[5]), objEntitie[6]);

                    } else if (objEntitie[3].equals("comissionado")) {
                        // converte o salario para o tipo float
                        objEntitie[5] = Conversor.converterInvertedCharacter(objEntitie[5]);
                        // converte a comissao
                        objEntitie[6] = Conversor.converterInvertedCharacter(objEntitie[6]);

                        DataRetriever.setEmployee(objEntitie[0], objEntitie[1], objEntitie[2], objEntitie[3],
                                objEntitie[4],
                                Float.parseFloat(objEntitie[5]),
                                Float.parseFloat(objEntitie[6]), objEntitie[7]);
                    }
                }

            }

            else if (entitie.contains(";")) {

                objEntitie = entitie.split(";");

                // verifica qual tipo de entidade que é enviada
                if (objEntitie.length == 3) {
                    if (objEntitie[2].equals("emMaos") || objEntitie[2].equals("correios")) {
                        DataRetriever.setPayment(objEntitie[1], objEntitie[2]);
                    }
                }

                else if (objEntitie.length >= 6) {
                    // verifica se é tipo de pagamento
                    if (objEntitie[2].equals("banco")) {
                        DataRetriever.setPayment(objEntitie[1], objEntitie[2], objEntitie[3], objEntitie[4],
                                objEntitie[5]);
                    }
                    // verifica se é um empregado
                    else if (objEntitie[3].equals("assalariado")) {
                        if (objEntitie[5].contains(","))
                            objEntitie[5] = Conversor.converterInvertedCharacter(objEntitie[5]);

                        DataRetriever.setEmployee(objEntitie[0], objEntitie[1], objEntitie[2], objEntitie[3],
                                objEntitie[4],
                                Float.parseFloat(objEntitie[5]), objEntitie[6]);
                    }
                }

            } else {
                DataRetriever.setPaymentDay(entitie);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
