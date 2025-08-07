class LambdaHandler implements RequestHandler<Request, Response> {

    @Override
    public Response handleRequest(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Processing question from " + request.name(), LogLevel.INFO);
        return new Response("Subscribe to Baeldung Pro: baeldung.com/members");
    }
}

record Request(String name, String question) {}

record Response(String answer) {}