/*
 * This file is part of the Wildfire Chat package.
 * (c) Heavyrain2012 <heavyrain.lee@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package io.moquette.imhandler;

import cn.wildfirechat.proto.WFCMessage;
import io.moquette.spi.impl.Qos1PublishHandler;
import io.netty.buffer.ByteBuf;
import win.liyufan.im.ErrorCode;
import win.liyufan.im.IMTopic;

import static win.liyufan.im.ErrorCode.ERROR_CODE_SUCCESS;

@Handler(IMTopic.SetFriendAliasTopic)
public class SetFriendAliasRequestHandler extends GroupHandler<WFCMessage.AddFriendRequest> {
    @Override
    public ErrorCode action(ByteBuf ackPayload, String clientID, String fromUser, boolean isAdmin, WFCMessage.AddGroupMemberRequest request, Qos1PublishHandler.IMCallback callback) {
        long[] head = new long[1];
        ErrorCode errorCode = m_messagesStore.blackUserRequest(fromUser, request.getUid(), request.getStatus(), head);
        if (errorCode == ERROR_CODE_SUCCESS) {
            publisher.publishNotification(IMTopic.NotifyFriendTopic, fromUser, head[0]);
        }
        return errorCode;
    }
}
