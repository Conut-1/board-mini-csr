export const server = {
    route: {
        auth: {
            login: '/api/auth/login',
            logout: "/api/auth/logout",
        },
        member: {
            me: "/api/member/me",
            unlock: (memberId) => `/api/member/unlock/${memberId}`,
            withdraw: "/api/member/withdraw",
            detail: (memberId) => `/api/member/detail/${memberId}`,
            list: '/api/member/list',
            register: '/api/member/register',
            isExistMemberId: "/api/member/isExistMemberId",
            update: "/api/member/update"
        },
        post: {
            create: "/api/post/create",
            detail: (postId) => `/api/post/detail/${postId}`,
            views: (postId) => `/api/post/detail/${postId}/views`,
            adminDelete: (postId) => `/api/post/admin/delete/${postId}`,
            delete: (postId) => `/api/post/delete/${postId}`,
            list: "/api/post/list",
            update: (postId) => `/api/post/update/${postId}`
        }
    }
};
