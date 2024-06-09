/*
Copyright 2016 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.pmam.fcm.notifications;

import androidx.core.app.NotificationCompat;

/**
 * Usamos um Singleton para uma cópia global do NotificationCompat.Builder para atualizar ativos
 * Notificações de outros serviços/atividades.
 * <p>
 * Você tem duas opções para atualizar suas notificações:
 * <p>
 * 1. Use um novo NotificationCompatBuilder para criar a Notificação. Essa abordagem exige que você
 * para obter *TODAS* as informações e passá-las ao construtor. Obtemos todas as informações de um Mock
 * Banco de dados e esta é a abordagem usada na MainActivity.
 * <p>
 * 2. Use um NotificationCompatBuilder existente para criar uma Notificação. Esta abordagem requer
 * você armazena uma referência ao construtor original. A vantagem é que você só precisa do novo/atualizado
 * informações para uma notificação existente. Usamos essa abordagem nos manipuladores IntentService para
 * atualizar notificações existentes.
 * <p>
 * IMPORTANT NOTE 1: Você não deve salvar/modificar o objeto Notification resultante usando
 * suas variáveis de membro e/ou APIs legadas. Se você quiser reter alguma coisa da atualização
 * para atualizar, mantenha o Builder conforme descrito na opção 2.
 * <p>
 * IMPORTANT NOTE 2: Se o Notification Builder global for perdido porque o processo foi encerrado, você
 * deve ter uma maneira de recriar o Notification Builder a partir de um estado persistente. (Fazemos isso como
 *bem na amostra, verifique o IntentServices.)
 */
public final class GlobalNotificationBuilder {

	public static int NOTIFICATION_ID = 113;
	private static NotificationCompat.Builder sGlobalNotificationCompatBuilder = null;

	/*
	 * Empty constructor - Não inicializamos o construtor porque dependemos de um estado nulo para nos permitir
	 *saber que o processo do aplicativo foi encerrado.
	 */
	private GlobalNotificationBuilder() {
	}

	public static NotificationCompat.Builder getNotificationCompatBuilderInstance() {
		return sGlobalNotificationCompatBuilder;
	}

	public static void setNotificationCompatBuilderInstance(NotificationCompat.Builder builder) {
		sGlobalNotificationCompatBuilder = builder;
	}
}
